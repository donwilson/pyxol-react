"use client";

import Link from "next/link";
import Image from "next/image";

import Container from "react-bootstrap/Container";

import SyntaxHighlighter from "react-syntax-highlighter/dist/esm/default-highlight";
import { dark } from "react-syntax-highlighter/dist/esm/styles/hljs";

//import ContactUsCTA from "../../../components/callToActions/contact_us";

export default function ArtViewer({ kind, title, image, readme, files }) {
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
						priority
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
				{files.map(({ file, type, contents }) => {
					return (
						<div key={file}>
							<h3 className="h3 mb-2">
								{file}
							</h3>
							
							<SyntaxHighlighter
								language={type}
								style={dark}
							>
								{contents}
							</SyntaxHighlighter>
						</div>
					);
				})}
			</>):""}
			
			{/*<ContactUsCTA />*/}
		</Container>
	</>);
}