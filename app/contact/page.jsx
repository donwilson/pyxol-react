"use client";

import Container from "react-bootstrap/Container";
import ContactForm from "./ContactForm";

export default function ContactPage() {
	return (<>
		<Container className="py-4">
			<h1 className="h1 mb-4">
				Contact Us
			</h1>
			
			<ContactForm />
		</Container>
	</>);
}