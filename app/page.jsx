"use client";

import { Container } from "react-bootstrap";
import SkyParticlesAnimation from "../components/animation/sky_particles";
import ServicesList from "./services/ServicesList";
import ToolsList from "./services/ToolsList";

import styles from "./styles.module.css";

export default function Page() {
	return (<>
		<style global jsx>{`
			body { background-color: #1b1b1b; }
		`}</style>
		
		<div className={styles.lead}>
			<SkyParticlesAnimation />
			
			<div className={styles.lead_title}>
				<h1 className={styles.h1}>
					Pyxol
				</h1>
				
				<p className={styles.subheader}>
					Creating meaningful technology
				</p>
			</div>
		</div>
		
		<div className="py-4 bg-white">
			<Container>
				<ServicesList />
				
				<hr />
				
				<ToolsList />
			</Container>
		</div>
	</>);
}
