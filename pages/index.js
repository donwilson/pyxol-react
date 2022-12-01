import Header from '../components/header';
import SkyParticlesAnimation from '../components/animation/sky_particles';

import styles from "../styles/homepage.module.css";

export default function Home() {
	return (
		<>
			<Header
				title="Build Great Things"
			/>
			
			<div className={styles.lead}>
				<SkyParticlesAnimation />
				
				<div className={styles.lead_title}>
					<h1 className={styles.h1}>
						Pyxol
					</h1>
					
					<p className={styles.subheader}>
						We aim to create meaningful technology
					</p>
				</div>
			</div>
			
			<style global jsx>{`
				body { background-color: #1b1b1b; }
			`}</style>
		</>
	)
}
