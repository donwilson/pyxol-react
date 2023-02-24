"use client";

import Container from "react-bootstrap/Container";

export default function Footer() {
	let date = new Date();
	const date_year = date.getFullYear();
	
	return (<>
		<Container className="py-4">
			<div className="d-flex flex-row justify-content-between">
				<div className="text-muted">
					pyxol &copy; {date_year}
				</div>
				
				<div className="text-muted">
					powered by next.js
				</div>
			</div>
		</Container>
	</>);
}