"use client";

import Link from "next/link";

import { Container } from "react-bootstrap"

import ContactUsCTA from "../../components/callToActions/contact_us";
import ServicesList from "./ServicesList";
import ToolsList from "./ToolsList";

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
			
			<h2 className="h3 py-3">
				Tools We Work With
			</h2>
			
			<ToolsList />
			
			<p className="text-muted py-2">
				Special thanks to <Link href="https://www.vectorlogo.zone/logos/index.html" target="_blank" className="text-muted"><em>VectorLogo.zone</em></Link> for logo sources
			</p>
			
			<ContactUsCTA />
		</Container>
	</>);
}