"use client";

import { useState } from "react";

import Alert from "react-bootstrap/Alert";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

import ProjectCard from "./ProjectCard";
//import ContactUsCTA from "../../components/callToActions/contact_us";

export default function ProjectsPage({ repos }) {
	const defaultSortBy = "most_watched";
	
	const [includeForked, setIncludeForked] = useState(0);
	const [sortBy, setSortBy] = useState(defaultSortBy);
	
	function handleForkedCheckbox(e) {
		setIncludeForked( (e.target.checked ? 1 : 0) );
	}
	
	function handleSortSelect(e) {
		if(e.target.value) {
			setSortBy(e.target.value);
		}
	}
	
	// filter(s)
	repos = repos.filter(repo => {
		if(!includeForked && repo.fork) {
			return false;
		}
		
		return true;
	});
	
	// sort
	if("recent" == sortBy) {
		// sort by recent
		repos = repos.sort((a, b) => {
			let dateA = new Date(a.created_at);
			let dateB = new Date(b.created_at);
			
			if(dateA < dateB) {
				return 1;
			} else if(dateB < dateA) {
				return -1;
			}
			
			return 0;
		});
	} else {
		// sort by most watched
		repos = repos.sort((a, b) => {
			if(a.watchers_count == b.watchers_count) {
				return 0;
			}
			
			return ((a.watchers_count < b.watchers_count)?1:-1);
		});
	}
	
	return (<>
		<Container className="py-4">
			<h1 className="h1 mb-4">
				Projects
			</h1>
			
			<p>Some of our open-source projects.</p>
			
			<div
				className="d-flex flex-row align-items-start justify-content-between pb-2 border-bottom mb-3"
			>
				<div>
					<strong className="mr-2">Sort by:</strong>
					<select
						defaultValue={defaultSortBy}
						onChange={e => handleSortSelect(e)}
					>
						<option value="most_watched">Most Watched</option>
						<option value="recent">Recent</option>
					</select>
				</div>
				
				<div>
					<strong className="mr-2">Include:</strong>
					<label>
						<input
							type="checkbox"
							className="mr-2"
							onClick={handleForkedCheckbox}
						/>
						Forked Repos
					</label>
				</div>
			</div>
			
			{repos ? (<>
				{repos.length ? (
					<Row>
						{repos.map(repo => (
							<Col
								md="4"
								key={repo.url}
							>
								<ProjectCard
									repo={repo}
								/>
							</Col>
						))}
					</Row>
				) : (
					<Alert
						variant="warning"
						className="text-center">
						Unable to fetch list of repositories, please check back soon
					</Alert>
				)}
			</>) : (
				<Alert
					variant="info"
					className="text-center">
					Loading repository list...
				</Alert>
			)}
			
			{/*<ContactUsCTA />*/}
		</Container>
	</>);
}