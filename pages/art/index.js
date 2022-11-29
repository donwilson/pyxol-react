import Head from "next/head";

import Container from "react-bootstrap/Container";

import Header from "../../components/header";
import Footer from "../../components/footer";
import ContactUsCTA from "../../components/callToActions/contact_us";

export default function Art() {
	
	
	return (
		<>
			<Head>
				<title>Art | Pyxol</title>
			</Head>
			
			<Header />
			
			<Container className="py-4">
				<h1 className="h1 mb-4">
					Art
				</h1>
				
				<p>@TODO</p>
				
				<ContactUsCTA />
			</Container>
			
			<Footer />
		</>
	);
}