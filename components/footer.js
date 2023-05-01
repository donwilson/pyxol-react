"use client";

import Link from "next/link";
import Script from "next/script";

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
					built with <Link href="https://react.dev/" target="_blank" className="text-muted font-weight-bold">React</Link> + <Link href="https://nextjs.org/" target="_blank" className="text-muted font-weight-bold">Next.js</Link>
				</div>
			</div>
		</Container>
		
		<Script async src="https://www.googletagmanager.com/gtag/js?id=G-YM0W9BHEND" strategy="afterInteractive"></Script>
		<Script id="google-analytics" strategy="afterInteractive">{`
			window.dataLayer = window.dataLayer || [];
			function gtag(){dataLayer.push(arguments);}
			gtag('js', new Date());
			
			gtag('config', 'G-YM0W9BHEND');
		`}</Script>
	</>);
}