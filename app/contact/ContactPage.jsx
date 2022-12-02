"use client";

import { Container } from "react-bootstrap"

import Header from "../../components/header";
import Footer from "../../components/footer";

export default function ContactPage() {
	return (
		<>
			<Header />
			
			<Container className="py-4">
				<h1 className="h1 mb-4">
					Contact Us
				</h1>
				
				<p>@TODO form</p>
			</Container>
			
			<Footer />
		</>
	)
}