const fs = require('fs/promises');

import { ts } from './time';

function cleanupGitHubRepoEntry(repo_entry) {
	let repo = {
		'title': repo_entry.name,
		'url': repo_entry.html_url,
		'language': repo_entry.language,
		'description': repo_entry.description,
		'stargazers_count': repo_entry.stargazers_count,
		'watchers_count': repo_entry.watchers_count,
		'forks_count': repo_entry.forks_count,
		'created_at': repo_entry.created_at,
		'fork': repo_entry.fork || false
	};
	
	return repo;
}

async function _pullGithubRepos(username) {
	let raw_repos = [];
	let fetch_link = `https://api.github.com/users/${username}/repos`;
	let fetched_links = [];
	
	do {
		//console.log(`fetchGithubRepos: fetching ${fetch_link}`);
		
		const res = await fetch(fetch_link);
		
		fetched_links.push(fetch_link);
		
		// reset fetch_link right away
		fetch_link = false;
		
		const data = await res.json();
		const headers = res.headers;
		
		// parse + merge data
		//console.log("data=", data);
		
		if(data && data.length) {
			data.map(entry => {
				let repo_save_data = cleanupGitHubRepoEntry(entry);
				
				raw_repos.push(repo_save_data);
			});
		}
		
		//console.log("headers:");
		//console.log(headers);
		
		if(headers.has('x-ratelimit-remaining')) {
			const ratelimit_remaining = headers.get('x-ratelimit-remaining');
			
			if(ratelimit_remaining <= 0) {
				console.log("Exceeded ratelimit for GitHub, exiting early");
				
				break;
			}
		}
		
		// find next page of repos (if any)
		if(headers.has('link')) {
			// parse headers.link response
			// 'link' => '<https://api.github.com/user/422444/repos?page=2>; rel="next", <https://api.github.com/user/422444/repos?page=2>; rel="last"',
			
			const link_value = headers.get('link');
			const link_match = link_value.match(/<([^>]+?)>;\s*rel=\"next\"/i);
			
			if(link_match && link_match[1]) {
				//console.log(link_match[1]);
				
				const matched_link = link_match[1];
				
				if(matched_link.match(/^https?\:\/\//i) && !fetched_links.includes(matched_link)) {
					fetch_link = matched_link;
				}
			} else {
				//console.log("no link match:", link_match);
			}
		}
	} while(fetch_link);
	
	return raw_repos;
}

function sortReposByDate(unsorted_repos) {
	// sort repos by unsorted_repos[0].created_at DESC
	unsorted_repos.sort((a, b) => {
		let dateA = new Date(a.created_at);
		let dateB = new Date(b.created_at);
		
		if(dateA < dateB) {
			return 1;
		} else if(dateB < dateA) {
			return -1;
		}
		
		return 0;
	});
	
	return unsorted_repos;
}

function sortReposByMostWatched(unsorted_repos) {
	// sort repos by unsorted_repos[0].created_at DESC
	unsorted_repos.sort((a, b) => {
		if(a.watchers_count == b.watchers_count) {
			return 0;
		}
		
		return ((a.watchers_count < b.watchers_count)?1:-1);
	});
	
	return unsorted_repos;
}

function _cacheReposKey(username) {
	return `github-repos-${username}`;
}

function _cacheReposFilepath(username) {
	const key = _cacheReposKey(username);
	
	return `./data/${key}.json`;
}

// check if file exists
async function _cacheFileExists(filepath) {
	//return !!(await fs.stat(filepath).catch(e => false));
	try {
		return await fs.stat(filepath).then(() => true).catch(() => false);
	} catch(err) {
		console.log("_cacheFileExists thrown err=", err);
	}
}

async function _getCachedRepos(username, default_return=false) {
	try {
		const filepath = _cacheReposFilepath(username);
		
		const file_exists = await _cacheFileExists(filepath);
		
		if(!file_exists) {
			throw "Cache file doesn't exist yet";
		}
		
		console.log("attempting to read file");
		
		let raw_cached_data = await fs.readFile(filepath, (err, data) => {
			if(err || !data) {
				console.log("err=", err);
				return false;
			}
			
			return data;
		});
		
		if(!raw_cached_data) {
			throw "Empty cache data";
		}
		
		const cached_data = JSON.parse(raw_cached_data);
		
		//console.log("cached_data=", cached_data);
		
		// @TODO check cached_data.timestamp_created to invalidate
		// @TODO use cached_data.data
		
		if(!cached_data || !cached_data.data) {
			throw "empty file";
		}
		
		if(cached_data.expiry && (cached_data.expiry <= ts())) {
			return default_return;
		}
		
		return cached_data.data;
	} catch(err) {
		// do nothing
		console.log("error reading existing file:", err);
	}
	
	return default_return;
}

async function _setCachedRepos(username, repos, expiry_seconds=86400) {
	try {
		const filepath = _cacheReposFilepath(username);
		
		console.log("Writing to", filepath);
		
		if(isNaN(expiry_seconds)) {
			expiry_seconds = (60 * 60 * 24);
		}
		
		const expiry_ts = (ts() + expiry_seconds);
		
		// format for cache object:
		// expiry: timestamp for when cache can expire
		// data: data to cache
		let save_data = JSON.stringify({
			expiry: expiry_ts,
			data: repos
		});
		
		await fs.writeFile(filepath, save_data, {
			flag: "w"
		});
	} catch(err) {
		// do nothing
		console.log("error writing cache file:", err);
	}
}

export async function fetchGithubRepos(username) {
	if(!username || !username.match(/^[A-Za-z0-9\-_]+$/i)) {
		throw "Invalid GitHub username";
	}
	
	// check if cached
	const cached_data = await _getCachedRepos(username);
	
	if(false !== cached_data) {
		//return sortReposByMostWatched(cached_data);
		return cached_data;
	}
	
	// fetch data
	let repos = [];
	
	try {
		let unsorted_repos = await _pullGithubRepos(username);
		
		repos = sortReposByMostWatched(unsorted_repos);
		
		//console.log("repos=", repos);
		
		if(!repos || !repos.length) {
			throw "repos array is empty, skipping write";
		}
		
		_setCachedRepos(username, repos, (60 * 60 * 24));
	} catch(err) {
		// do nothing
		console.log("error while writing file:", err);
	}
	
	return repos;
}