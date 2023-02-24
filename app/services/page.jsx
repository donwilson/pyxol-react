"use client";

import { Container, Row, Col } from "react-bootstrap"

import ContactUsCTA from "../../components/callToActions/contact_us";
import ServicesList from "./ServicesList";
import ProgrammingLanguagesList from "./ProgrammingLanguagesList";

export default function Services() {
	return (<>
		<Container className="py-4">
			<h1 className="h1 mb-4">
				Web Development Services
			</h1>
			
			<p>
				A list of various web development technologies we actively support.
			</p>
			
			<ServicesList />
			
			<hr />
			
			<ProgrammingLanguagesList />
			
			<ContactUsCTA />
		</Container>
	</>);
}