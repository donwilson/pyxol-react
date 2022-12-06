const fs = require('fs/promises');

function cleanupGitHubRepoEntry(repo_entry) {
	let repo = {
		'title': repo_entry.name,
		'url': repo_entry.html_url,
		'language': repo_entry.language,
		'description': repo_entry.description,
		'stargazers_count': repo_entry.stargazers_count,
		'watchers_count': repo_entry.watchers_count,
		'forks_count': repo_entry.forks_count,
		'created_at': repo_entry.created_at
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
	
	return repos;
}

function _sortRepos(unsorted_repos) {
	let repos = unsorted_repos;
	
	// @TODO sort repos by unsorted_repos[0].created_at DESC
	
	return repos;
}

function _cacheReposKey(username) {
	return `github-repos-${username}`;
}

function _cacheReposFilepath(username) {
	const key = _cacheReposKey(username);
	
	return `./data/${key}.json`;
}

async function _getCachedRepos(username, default_return=false) {
	try {
		const filepath = _cacheReposFilepath(username);
		
		let raw_cached_data = await fs.readFile(filepath);
		
		if(!raw_cached_data) {
			throw "Empty cache data";
		}
		
		const cached_data = JSON.parse(raw_cached_data);
		
		//console.log("cached_data=", cached_data);
		
		// @TODO check cached_data.timestamp_created to invalidate
		// @TODO use cached_data.data
		
		if(!cached_data || !cached_data.length) {
			throw "empty file";
		}
		
		return cached_data;
	} catch(err) {
		// do nothing
		console.log("error reading existing file:", err);
	}
	
	return default_return;
}

async function _setCachedRepos(username, repos) {
	try {
		const filepath = _cacheReposFilepath(username);
		
		console.log("Writing to", filepath);
		
		let save_data = JSON.stringify(repos);
		
		// @TODO format data for caching:
		// @TODO use .timestamp_created to expire cache
		// @TODO use .data to store cache data
		
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
	const cached_data = _getCachedRepos(username);
	
	if(false !== cached_data) {
		return cached_data;
	}
	
	// fetch data
	try {
		let unsorted_repos = await _pullGithubRepos(username);
		
		const repos = _sortRepos(unsorted_repos);
		
		//console.log("repos=", repos);
		
		if(!repos || !repos.length) {
			throw "repos array is empty, skipping write";
		}
		
		_setCachedRepos(username, repos);
	} catch(err) {
		// do nothing
		console.log("error while writing file:", err);
	}
	
	return repos;
}