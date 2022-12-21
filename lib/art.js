export function parseArtSlug(slug) {
	let bits = slug.split("-");
	
	return {
		kind: bits.shift(),
		project_key: bits.join("-")
	};
}

export function parseArtTitleFromFolderName(art_folder) {
	return art_folder.replace(/[\-_]+/g, " ").replace(/\b[a-z]/g, firstLetter => firstLetter.toUpperCase());
}

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