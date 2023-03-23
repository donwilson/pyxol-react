"use client";

import Link from "next/link";
import Image from "next/image";

import Container from "react-bootstrap/Container";
import FileSourceViewer from "./FileSourceViewer";
import MarkdownViewer from "./MarkdownViewer";

export default function ArtViewer({ kind, title, image, readme, baseDir, files }) {
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
				<div className="d-block bg-dark text-center p-2 mb-4">
					<Image
						src={image.url}
						width={image.width}
						height={image.height}
						alt={`Preview image for ${title}`}
						className="img-fluid"
						priority
					/>
				</div>
			</>):""}
			
			{readme ? (<>
				<MarkdownViewer
					baseDir={baseDir}
					parsedMarkdownHTML={readme}
				/>
				
				<hr />
			</>):""}
			
			{files && files.length ? (<>
				{files.map(({ file, type, contents }) => {
					return (<FileSourceViewer key={file} file={file} type={type} contents={contents} />);
				})}
			</>):""}
		</Container>
	</>);
}