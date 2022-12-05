import { Suspense } from "react";
import { fetchGithubRepos } from "../../lib/github";
import ProjectsPage from "./ProjectsPage";

// tmp
export const revalidate = 60;
// tmp

async function getData() {
	const github_repos = await fetchGithubRepos(process.env.GITHUB_USERNAME);
	
	//console.log("github_repos=", github_repos);
	
	return github_repos;
}

export default async function Projects() {
	let github_repos = await getData();
	
	return (
		<>
			<Suspense fallback={<p>Loading data...</p>}>
				<ProjectsPage
					repos={github_repos}
				/>
			</Suspense>
		</>
	)
}