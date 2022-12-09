"use client";

import Link from "next/link";

import Container from "react-bootstrap/Container";

import ContactUsCTA from "../../../components/callToActions/contact_us";

export default function ArtViewPage({ id }) {
	return (<>
		<Container className="py-4">
			<h1 className="h1 mb-4">
				<span class="text-muted">
					<Link
						href="/art/"
						className="text-secondary mr-1"
					>
						Art
					</Link>
					&rsaquo;
				</span>
				{id}
			</h1>
			
			<p>@TODO</p>
			
			<ContactUsCTA />
		</Container>
	</>);
}