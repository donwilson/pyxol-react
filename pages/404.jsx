import Header from "../components/header";
import Footer from "../components/footer";
import Container from "react-bootstrap/Container";

import Link from "next/link";

export default function NotFound() {
	return (<>
		<Header />
		
		<Container className="py-4">
			<div className="h3">
				Page Not Found
			</div>
			<p>The page you requested was not found. <Link href="/">Return Home</Link></p>
		</Container>
		
		<Footer />
	</>);
}