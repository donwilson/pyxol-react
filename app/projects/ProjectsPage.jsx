"use client";

import { Container } from "react-bootstrap"

import Header from "../../components/header";
import Footer from "../../components/footer";
import ContactUsCTA from "../../components/callToActions/contact_us";

export default function ProjectsPage() {
	return (
		<>
			<Header />
			
			<Container className="py-4">
				<h1 className="h1 mb-4">
					Projects
				</h1>
				
				<p>Publicly available source code for some of our various projects.</p>
				
				<ContactUsCTA />
			</Container>
			
			<Footer />
		</>
	)
}