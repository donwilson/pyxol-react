export default function Head({ id }) {
	return (<>
		<title>{`${id} - Art | ${process.env.NEXT_PUBLIC_SITE_NAME}`}</title>
	</>);
}