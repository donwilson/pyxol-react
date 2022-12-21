"use client";

import Link from "next/link";
import Image from "next/image";

import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

import SyntaxHighlighter from "react-syntax-highlighter/dist/esm/default-highlight";
import { dark } from "react-syntax-highlighter/dist/esm/styles/hljs";

import ContactUsCTA from "../../../components/callToActions/contact_us";
import { Nav, Tab } from "react-bootstrap";

export default function ArtViewer({ kind, title, image, readme, files }) {
	let first_filename = false;
	
	if(files && files.length) {
		for(let file in files) {
			first_filename = file.file;
			
			break;
		}
	}
	
	return (<>
		<Container className="py-4">
			<h1 className="h1 mb-4">
				<span className="text-muted">
					<Link
						href="/art/"
						className="text-secondary mr-1"
					>
						Art
					</Link>
					<span className="mx-1">&rsaquo;</span>
				</span>
				{title}
			</h1>
			
			{image && image.url ? (<>
				<div className="d-block mb-4">
					<Image
						src={image.url}
						width={image.width}
						height={image.height}
						alt={`Preview image for ${title}`}
					/>
				</div>
			</>):""}
			
			{readme ? (<>
				<h2 className="h4 mb-1">
					Readme
				</h2>
				
				<SyntaxHighlighter
					language="markdown"
					style={dark}
				>
					{readme}
				</SyntaxHighlighter>
				
				<hr />
			</>):""}
			
			{files && files.length ? (<>
				<Tab.Container id="left-tabs" defaultActiveKey={first_filename}>
					<Row>
						<Col xs="12" sm="3">
							<Nav
								variant="tabs"
								className="flex-column"
							>
								{files.map(({ file }) => {
									return (
										<Nav.Item
											key={file}
										>
											<Nav.Link eventKey={file}>
												{file}
											</Nav.Link>
										</Nav.Item>
									);
								})}
							</Nav>
						</Col>
						<Col xs="12" sm="9">
							<Tab.Content>
								{files.map(({ file, type, contents }) => {
									return (
										<Tab.Pane
											key={file}
											eventKey={file}
										>
											<h3 class="h3 mb-2">
												{file}
											</h3>
											
											<SyntaxHighlighter
												language={type}
												style={dark}
											>
												{contents}
											</SyntaxHighlighter>
										</Tab.Pane>
									);
								})}
							</Tab.Content>
						</Col>
					</Row>
				</Tab.Container>
			</>):""}
			
			<ContactUsCTA />
		</Container>
	</>);
}