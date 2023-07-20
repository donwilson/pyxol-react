import { getArtItems, getArtImageDetails, parseArtSlug, parseArtTitleFromFolderName } from "../../../lib/art";

import { remark } from "remark";
import html from "remark-html";

import ArtViewer from "./ArtViewer";

const fs = require("fs/promises");

async function parseMarkdownContents(contents) {
	const processedContent = await remark().use(html).process(contents);
	const contentHtml = processedContent.toString();
	
	return contentHtml;
}

async function getData(slug) {
	const { kind, project_key } = parseArtSlug(slug);
	
	const title = parseArtTitleFromFolderName(project_key);
	
	const base_dir = `public/content/${kind}_projects/${project_key}`;
	
	let files = [];
	let readme = false;
	
	let raw_files = await fs.readdir(base_dir);
	
	raw_files.sort((a, b) => {
		if(a == b) {
			return 0;
		}
		
		return ((a < b)?-1:1);
	});
	
	for(let file of raw_files) {
		const filepath = `${base_dir}/${file}`;
		
		// skip library files
		if(file.match(/^(p5\.dom\.js|p5\.js)$/gi)) {
			//console.log("skipping", file);
			continue;
		}
		
		// only allow specific file types
		if(!file.match(/\.(md|pde|js|html)$/gi)) {
			//console.log("skipping2", file);
			continue;
		}
		
		if("readme.md" === file.toLowerCase()) {
			readme = await fs.readFile(filepath, {
				encoding: "utf8"
			});
			
			readme = await parseMarkdownContents(readme);
		} else {
			// check if directory
			const filestat = await fs.stat(filepath);
			
			if(filestat.isDirectory()) {
				continue;
			}
			
			let filetype = "javascript";
			
			if(file.match(/\.html$/i)) {
				filetype = "html";
			} else if(file.match(/\.pde$/i)) {
				filetype = "processing";
			} else if(file.match(/\.class$/i)) {
				filetype = "processing";
			}
			
			// success
			files.push({
				file,
				type: filetype,
				'contents': await fs.readFile(filepath, {
					encoding: "utf8"
				})
			});
		}
	}
	
	return {
		kind: kind,
		title: title,
		image: getArtImageDetails(kind, project_key),
		readme: readme,
		baseDir: base_dir,
		files: files
	}
}

export async function generateMetadata({ params }) {
	let page_title = `Art | ${process.env.NEXT_PUBLIC_SITE_NAME}`;
	
	if(params.slug) {
		const { project_key } = parseArtSlug(params.slug);
		const art_item_title = parseArtTitleFromFolderName(project_key);
		
		page_title = `${art_item_title} Art | ${process.env.NEXT_PUBLIC_SITE_NAME}`;
	}
	
	return {
		'title': page_title
	};
}

export async function generateStaticParams() {
	const art_items = await getArtItems();
	
	return art_items.map((art_item) => ({
		slug: art_item.slug,
	}))
}

export default async function ArtViewPage({ params }) {
	const art_details = await getData(params.slug);
	
	return (<>
		<ArtViewer
			{...art_details}
		/>
	</>);
}