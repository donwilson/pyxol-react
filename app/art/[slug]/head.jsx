import { parseArtSlug, parseArtTitleFromFolderName } from "../../../lib/art";
import DefaultHeadTags from "../../default-head-tags";

export default function Head({ params }) {
	if(!params.slug) {
		return (<>
			<title>{`Art | ${process.env.NEXT_PUBLIC_SITE_NAME}`}</title>
		</>);
	}
	
	const { project_key } = parseArtSlug(params.slug);
	
	const title = parseArtTitleFromFolderName(project_key);
	
	return (<>
		<DefaultHeadTags />
		
		<title>{`Art - ${title} | ${process.env.NEXT_PUBLIC_SITE_NAME}`}</title>
	</>);
}