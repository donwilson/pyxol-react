import Image from "next/image";

import { Row, Col } from "react-bootstrap";

export default function ServicesList() {
	return (<>
		<Row>
			<Col
				sm="4"
				className="text-center"
			>
				<Image
					src="/images/services/logo-wordpress.png"
					width="2000"
					height="680"
					alt="WordPress Logo"
					className="img-responsive"
					style={{
						width: "100%",
						maxWidth: "300px",
						height: "auto"
					}}
				/>
			</Col>
			<Col sm="8">
				<h2 className="h3">
					WordPress
				</h2>
				
				<p>We have 12 years of experience in WordPress custom theme and plugin development, fine tuning, PageSpeed improvements, and more. We also provide recovery for websites that have been hacked and other disaster situations. If your WordPress website has been hacked and it&apos;s no longer functioning as it should, we&apos;re able to quickly get the website back up and running. We also offer consultation for how to better secure your website, what and when to upgrade themes and plugins, and other improvements to speed up your WordPress-based website.</p>
			</Col>
		</Row>
		
		<hr />
		
		<Row>
			<Col
				sm="4"
				className="text-center"
			>
				<Image
					src="/images/services/logo-nextjs.jpg"
					width="1400"
					height="700"
					alt="Next.js Logo"
					className="img-responsive"
					style={{
						width: "100%",
						maxWidth: "300px",
						height: "auto"
					}}
				/>
			</Col>
			<Col sm="8">
				<h2 className="h3">
					Next.js
				</h2>
				<p>A fast web development framework that enables React-based applications with server-side rendering and generated static websites. This very website is built using this technology.</p>
			</Col>
		</Row>
		
		<hr />
		
		<Row>
			<Col
				sm="4"
				className="text-center"
			>
				<Image
					src="/images/services/logo-react.png"
					width="870"
					height="500"
					alt="React.js Logo"
					className="img-responsive"
					style={{
						width: "100%",
						maxWidth: "300px",
						height: "auto"
					}}
				/>
			</Col>
			<Col sm="8">
				<h2 className="h3">
					React
				</h2>
				<p>A front-end JavaScript library for building user interfaces with a focus on functionality separation using a component-based architecture.</p>
			</Col>
		</Row>
		
		<hr />
		
		<Row>
			<Col
				sm="4"
				className="text-center"
			>
				<Image
					src="/images/services/logo-mariadb.jpg"
					width="1024"
					height="512"
					alt="MariaDB Logo"
					className="img-responsive"
					style={{
						width: "100%",
						maxWidth: "300px",
						height: "auto"
					}}
				/>
			</Col>
			<Col sm="8">
				<h2 className="h3">
					MariaDB
				</h2>
				<p>A fork of the popular MySQL relational database. We provide expert-level knowledge and experience regarding data structure design, performance oriented query optimization, daemon-level configuration, backup and disaster recovery solutions.</p>
			</Col>
		</Row>
		
		<hr />
		
		<Row>
			<Col
				sm="4"
				className="text-center"
			>
				<Image
					src="/images/services/logo-core-web-vitals.jpg"
					width="1200"
					height="600"
					alt="Core Web Vitals Logo"
					className="img-responsive"
					style={{
						width: "100%",
						maxWidth: "300px",
						height: "auto"
					}}
				/>
			</Col>
			<Col sm="8">
				<h2 className="h3">
					Core Web Vitals
				</h2>
				<p>A vital list of metrics for improving not only speed and user experience but search engine placement. We provide solutions to improving scores on:</p>
				<ul>
					<li>Largest Contental Paint (<abbr className="font-weight-bold">LCP</abbr>) is a measurement for loading performance</li>
					<li>First Input Delay (<abbr className="font-weight-bold">FID</abbr>) is a measurement of delay in interactivity</li>
					<li>Cumulative Layout Shift (<abbr className="font-weight-bold">CLS</abbr>) is a measurement in visual stability</li>
				</ul>
			</Col>
		</Row>
		
		<hr />
		
		<Row>
			<Col
				sm="4"
				className="text-center"
			>
				<Image
					src="/images/services/logo-cloud.png"
					width="520"
					height="268"
					alt="Cloud Service Logos: AWS, Google Cloud, Azure"
					className="img-responsive"
					style={{
						width: "100%",
						maxWidth: "300px",
						height: "auto"
					}}
				/>
			</Col>
			<Col sm="8">
				<h2 className="h3">
					Cloud Architect
				</h2>
				<p>Cloud orchestration is at the heart of most major businesses and it is imperative to not only use the right tools for the job, but to seemlessly guide data through the most cost efficient way possible.</p>
			</Col>
		</Row>
		
		<hr />
		
		<Row>
			<Col
				sm="4"
				className="text-center"
			>
				<Image
					src="/images/services/logo-docker.png"
					width="3840"
					height="2160"
					alt="Docker Logo"
					className="img-responsive"
					style={{
						width: "100%",
						maxWidth: "300px",
						height: "auto"
					}}
				/>
			</Col>
			<Col sm="8">
				<h2 className="h3">
					Docker
				</h2>
				<p>Containerizing service daemons provides developers with the ability to quickly design, prototype, test, and reliably debug software. Docker provides more control over OS/daemon versioning and leads to less time dealing with routine tasks. A vital tool, Docker creates less headache with minimal impact to performance.</p>
			</Col>
		</Row>
		
		<hr />
		
		<Row>
			<Col
				sm="4"
				className="text-center"
			>
				<Image
					src="/images/services/logo-debian.jpg"
					width="635"
					height="307"
					alt="Debain Logo"
					className="img-responsive"
					style={{
						width: "100%",
						maxWidth: "300px",
						height: "auto"
					}}
				/>
			</Col>
			<Col sm="8">
				<h2 className="h3">
					Linux Systems Administration
				</h2>
				<p>We provide a security-first approach to our server design and system administration services. Our services include planning and systems architecture, migration, performance tuning and general maintenance.</p>
			</Col>
		</Row>
	</>);
}