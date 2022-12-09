import Link from "next/link";

import Card from "react-bootstrap/Card";
import Badge from "react-bootstrap/Badge";

export default function ProjectCard({ repo }) {
	const {
		url,
		language,
		fork,
		watchers_count,
		description,
		title
	} = repo;
	
	return (
		<Card
			className="mb-4 shadow-sm"
		>
			<Card.Body>
				<h3 className="h3">
					<Link
						href={url}
						target="_blank"
						className="text-dark"
						style={{
							textDecoration: "underline"
						}}
					>
						{title}
					</Link>
				</h3>
				
				<Card.Text>
					{language ? (<>
						<Badge
							variant="primary"
							className="mr-2 rounded-0"
						>
							{language}
						</Badge>
					</>):""}
					
					{fork ? (<>
						<small
							className="mr-2 text-muted"
						>
							Forked
						</small>
					</>):""}
					
					{watchers_count ? (<>
						<small
							className="mr-2 text-muted"
						>
							{`${watchers_count} watching`}
						</small>
					</>):""}
					
					{description ? (<>
						{description}
					</>):""}
				</Card.Text>
				
				<div
					className="d-flex justify-content-between align-items-center"
				>
					{url ? (<>
						<Link
							href={url}
							target="_blank"
							className="btn btn-sm btn-secondary"
						>
							View Code
						</Link>
					</>):""}
				</div>
			</Card.Body>
		</Card>
	);
}