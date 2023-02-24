import DefaultHeadTags from "../default-head-tags";

export default function Head() {
	return (<>
		<DefaultHeadTags />
		
		<title>{`Open Source Projects | ${process.env.NEXT_PUBLIC_SITE_NAME}`}</title>
	</>);
}