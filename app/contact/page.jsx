"use client";

import Container from "react-bootstrap/Container";

export default function ContactPage() {
	return (<>
		<Container className="py-4">
			<h1 className="h1 mb-4">
				Contact Us
			</h1>
			
			<div className="my-4 text-center">
				<p>If you would like to  or get a quote on a project, please email us at the address below.</p>
				
				<p><a href="mailto:contact&#64;pyxol&#46;com" class="btn btn-secondary">contact&#64;pyxol&#46;com</a></p>
			</div>
		</Container>
	</>);
}