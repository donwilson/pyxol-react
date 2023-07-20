import { Suspense } from "react";

import { getArtItems } from "../../lib/art";

import ArtPage from "./ArtPage";

export default async function Art() {
	const art_items = await getArtItems();
	
	return (<>
		<Suspense fallback={<p>Loading data...</p>}>
			<ArtPage
				art_items={art_items}
			/>
		</Suspense>
	</>);
}