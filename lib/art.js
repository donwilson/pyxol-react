const fs = require('fs/promises');

/**
 * Get all art items
 * @returns array
 */
export async function getArtItems() {
	const project_types = await fs.readdir("public/content/");
	
	let items = [];
	
	for(let project_type of project_types) {
		let kind = "processing";
		
		if("p5_projects" === project_type) {
			kind = "p5";
		}
		
		let projects = await fs.readdir(`public/content/${project_type}/`);
		
		projects.forEach(project => {
			const title = project.replace(/_+/g, " ").replace(/\b[a-z]/g, letter => letter.toUpperCase());
			const slug = `${kind}-${project}`;
			const url = `/art/${slug}/`;
			const thumb = getArtImageDetails(kind, project);
			
			items.push({
				kind: kind,
				project: project,
				slug: slug,
				title: title,
				url: url,
				thumb: thumb
			});
		});
	}
	
	// sort
	items.sort((a, b) => {
		if(a.title == b.title) {
			return 0;
		}
		
		return ((a.title < b.title)?-1:1);
	});
	
	return items;
}

/**
 * Parse an art URL slug into an object containing keys 'kind' and 'project_key'
 * @param {string} slug Raw slug from URL
 * @returns object
 */
export function parseArtSlug(slug) {
	let bits = slug.split("-");
	
	return {
		kind: bits.shift(),
		project_key: bits.join("-")
	};
}

/**
 * Generate the page title from the art folder name
 * @param {string} art_folder The art folder name
 * @returns string
 */
export function parseArtTitleFromFolderName(art_folder) {
	return art_folder.replace(/[\-_]+/g, " ").replace(/\b[a-z]/g, firstLetter => firstLetter.toUpperCase());
}

/**
 * Get the preview image details for a given art folder
 * @param {string} kind The parent folder name to this art folder
 * @param {string} art_folder The art folder name
 * @returns object
 */
export function getArtImageDetails(kind, art_folder) {
	const path = `/content/${kind}_projects/${art_folder}/preview.png`;
	
	const sizeOf = require('image-size');
	const dimensions = sizeOf(`public${path}`);
	
	return {
		url: path,
		width: dimensions.width,
		height: dimensions.height
	}
}