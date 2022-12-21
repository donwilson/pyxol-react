import { Suspense } from "react";
import { getArtImageDetails } from "../../lib/art";

import ArtPage from "./ArtPage";

const fs = require('fs/promises');
const sizeOf = require('image-size');

async function getDirListing(dir) {
	return await fs.readdir(dir);
}

async function getData() {
	const project_types = await getDirListing("public/content/");
	
	let items = [];
	
	for(let project_type of project_types) {
		let kind = "processing";
		
		if("p5_projects" === project_type) {
			kind = "p5";
		}
		
		let projects = await getDirListing(`public/content/${project_type}/`);
		
		projects.forEach(project => {
			const title = project.replace(/_+/g, " ").replace(/\b[a-z]/g, letter => letter.toUpperCase());
			const url = `/art/${kind}-${project}/`;
			const thumb = getArtImageDetails(kind, project);
			
			items.push({
				kind: kind,
				title: title,
				url: url,
				thumb: thumb
			});
		});
	}
	
	//console.log("items=", items);
	
	// sort
	items.sort((a, b) => {
		if(a.title == b.title) {
			return 0;
		}
		
		return ((a.title < b.title)?-1:1);
	});
	
	//console.log(items);
	
	return items;
}

export default async function Art() {
	const art_items = await getData();
	
	return (<>
		<Suspense fallback={<p>Loading data...</p>}>
			<ArtPage
				art_items={art_items}
			/>
		</Suspense>
	</>);
}