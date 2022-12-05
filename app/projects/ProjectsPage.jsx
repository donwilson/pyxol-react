"use client";

import Link from "next/link";

import { Alert, Container, Row, Col, Card } from "react-bootstrap"

import Header from "../../components/header";
import Footer from "../../components/footer";
import ContactUsCTA from "../../components/callToActions/contact_us";

export default function ProjectsPage({ repos }) {
	console.log("repos=", repos);
	
	return (
		<>
			<Header />
			
			<Container className="py-4">
				<h1 className="h1 mb-4">
					Projects
				</h1>
				
				<p>Publicly available source code for some of our various projects.</p>
				
				{repos ? (
					<>
						{repos.length ? (
							<>
								<Row>
									{repos.map(repo => (
										<Col
											key={repo.full_name}
											md="4"
										>
											<Card
												className="mb-4 shadow-sm"
											>
												<Card.Body>
													<h3 class="h3">
														{repo.name}
													</h3>
													
													{repo.language || repo.description ? (
														<>
															<Card.Text>
																{repo.language ? (
																	<>
																		<strong>{repo.language}</strong>
																	</>
																):""}
																
																{repo.language && repo.description ? (
																	<>
																		&ndash;
																	</>
																):""}
																
																{repo.description ? (
																	<>
																		{repo.description}
																	</>
																):""}
															</Card.Text>
															
															<div
																className="d-flex justify-content-between align-items-center"
															>
																{repo.html_url ? (
																	<Link
																		href={repo.href}
																		target="_blank"
																		className="btn btn-sm btn-secondary"
																	>
																		View Code
																	</Link>
																):""}
															</div>
														</>
													) : ""}
												</Card.Body>
											</Card>
										</Col>
									))}
								</Row>
							</>
						) : (
							<>
								<Alert
									variant="warning"
									className="text-center">
									Unable to fetch list of repositories, please check back soon
								</Alert>
							</>
						)}
					</>
				) : (
					<>
						<Alert
							variant="info"
							className="text-center">
							Loading repository list...
						</Alert>
					</>
				)}
				
				<ContactUsCTA />
			</Container>
			
			<Footer />
		</>
	)
}