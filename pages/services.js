import Head from "next/head";
import Image from "next/image";

import { Container, Row, Col } from "react-bootstrap";
import Button from "react-bootstrap/Button";

import Header from "../components/header";
import Footer from "../components/footer";

export default function Services() {
	
	
	return (
		<>
			<Head>
				<title>Web Development Services | Pyxol</title>
			</Head>
			
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
						<p>We have over 10 years of experience in WordPress custom development, fine tuning, PageSpeed improvements, and more. We also provide recovery for websites that have been hacked and other disaster situations. If your WordPress website has been hacked and it's no longer functioning as it should, we're able to quickly get the website back up and running. We also offer consultation for how to better secure your website, what and when to upgrade themes and plugins, and other improvements to speed up your WordPress-based website.</p>
					</Col>
				</Row>
				
				<div
					className="bg-secondary text-white text-center mt-4 p-4"
				>
					please visit our
					<Button
						variant="light"
						href="/contact/"
						title="Get in touch with pyxol"
						className="text-secondary mx-2 font-weight-bold"
					>
						Contact Us
					</Button>
					page to get in touch
				</div>
			</Container>
			
			<Footer />
		</>
	);
}