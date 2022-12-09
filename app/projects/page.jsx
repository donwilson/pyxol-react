import { Suspense } from "react";

import { fetchGithubRepos } from "../../lib/github";

import ProjectsPage from "./ProjectsPage";

export default async function Projects() {
	let github_repos = await fetchGithubRepos(process.env.GITHUB_USERNAME);
	
	return (<>
		<Suspense fallback={<p>Loading data...</p>}>
			<ProjectsPage
				repos={github_repos}
			/>
		</Suspense>
	</>);
}