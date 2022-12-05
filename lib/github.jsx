

export async function fetchGithubRepos(username) {
	if(!username || !username.match(/^[A-Za-z0-9\-_]+$/i)) {
		throw "Invalid GitHub username";
	}
	
	let repos = [];
	
	// @TODO figure out cache for this fetch process
	
	let fetch_link = `https://api.github.com/users/${username}/repos`;
	let fetched_links = [];
	
	do {
		console.log(`fetchGithubRepos: fetching ${fetch_link}`);
		const res = await fetch(fetch_link);
		
		fetched_links.push(fetch_link);
		
		// reset fetch_link right away
		fetch_link = false;
		
		const data = await res.json();
		const headers = res.headers;
		
		// parse data
		//console.log("data=", data);
		
		if(data && data.length) {
			const merged_repos = repos.concat(data);
			
			repos = merged_repos;
		}
		
		//console.log("headers:");
		console.log(headers);
		
		if(headers.has('x-ratelimit-remaining')) {
			const ratelimit_remaining = headers.get('x-ratelimit-remaining');
			
			if(ratelimit_remaining <= 0) {
				console.log("Passed ratelimit from GitHub, exiting early");
				
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
	
	console.log("repos=", repos);
	
	return repos;
}