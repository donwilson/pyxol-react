"use client";

import Image from "next/image";

import { Container, Row, Col } from "react-bootstrap"

import Header from "../../components/header";
import Footer from "../../components/footer";
import ContactUsCTA from "../../components/callToActions/contact_us";

export default function ServicesPage() {
	return (
		<>
			<Header />
			
			<Container className="py-4">
				<h1 className="h1 mb-4">
					Services
				</h1>
				
				<Row>
					<Col
						sm="4"
						className="text-center"
					>
						<Image
							src="/images/services/logo-wordpress.png"
							width="2000"
							height="680"
							alt="WordPress Logo"
							className="img-responsive"
							style={{
								width: "100%",
								maxWidth: "300px",
								height: "auto"
							}}
						/>
					</Col>
					<Col sm="8">
						<p>We have over 10 years of experience in WordPress custom development, fine tuning, PageSpeed improvements, and more. We also provide recovery for websites that have been hacked and other disaster situations. If your WordPress website has been hacked and it&apos;s no longer functioning as it should, we&apos;re able to quickly get the website back up and running. We also offer consultation for how to better secure your website, what and when to upgrade themes and plugins, and other improvements to speed up your WordPress-based website.</p>
					</Col>
				</Row>
				
				<ContactUsCTA />
			</Container>
			
			<Footer />
		</>
	)
}