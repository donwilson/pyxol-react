import Image from "next/image";

import { Row, Col, Card, CardColumns } from "react-bootstrap";

export default function ProgrammingLanguagesList() {
	const toolsets = [
		{
			'title': "Programming Languages / Syntax",
			'tools': {
				'css': "CSS",
				'html5': "HTML5",
				//'javascript': "JavaScript",
				'java': "Java",
				'perl': "Perl",
				'php': "PHP",
				'sass': "SASS",
				'typescript': "TypeScript",
				'xml': "XML",
			},
		},
		{
			'title': "Libraries",
			'tools': {
				//'handlebarsjs': "handlebars.js",
				//'fontawesome': "Font Awesome",
				'jquery': "jQuery",
				'laravel': "Laravel",
				//'phaserio': "phaser.io",
				'react': "React",
				//'vuejs': "VueJS",
				'wordpress': "WordPress",
			},
		},
		{
			'title': "Daemons",
			'tools': {
				'apache_solr': "Apache SOLR",
				'apache': "Apache",
				'graphql': "GraphQL",
				'mariadb': "MariaDB",
				'memcached': "memcached",
				'minioio': "minio",
				'mongodb': "mongoDB",
				'mysql': "MySQL",
				'nginx': "nginx",
				'nodejs': "Node.js",
				'rabbitmq': "RabbitMQ",
				'sqlite': "SQLite",
				'traefikio': "Traefik",
				'vagrantup': "Vagrant UP",
				'varnish-cache': "Varnish Cache",
				'redis': "Redis",
			},
		},
		{
			'title': "Cloud / Hosting Providers",
			'tools': {
				//'aws_lambda': "AWS Lambda",
				'aws': "AWS",
				'cloudflare': "CloudFlare",
				'digitalocean': "DigitalOcean",
				'godaddy': "GoDaddy",
				'google_cloud': "Google Cloud",
				'letsencrypt': "Let's Encrypt",
				'heroku': "Heroku",
				'mailgun': "Mailgun",
				'networksolutions': "NetworkSolutions",
				'linode': "Linode",
				'rackspace': "RackSpace",
			},
		},
		{
			'title': "Operating Systems",
			'tools': {
				'centos': "CentOS",
				'debian': "Debian",
				'redhat': "RedHat",
			},
		},
		{
			'title': "Toolchain",
			'tools': {
				'bitbucket': "BitBucket",
				'bitnami': "Bitnami",
				'curl_haxx': "cURL",
				'docker': "Docker",
				'edgewall_trac': "trac",
				'git-scm': "Git",
				'github': "GitHub",
				'gitlab': "GitLab",
				'haproxy': "HAProxy",
				'js_webpack': "WebPack",
				'kubernetes': "Kubernetes",
				'firefox': "Firefox",
				'google_chrome': "Google Chrome",
				'grafana': "Grafana",
				'rancher': "Rancher",
				'tailwindcss': "Tailwind CSS",
				'opengraph': "OpenGraph",
				'microsoft_edge': "Microsoft Edge",
				'raspberrypi': "Raspberry PI",
				'visualstudio_code': "Visual Studio Code",
				'npmjs': "npm",
				'sketchapp': "SketchApp",
				'slack': "Slack",
				'trello': "Trello",
				'virtualbox': "VirtualBox",
			},
		},
		{
			'title': "Vendors / APIs",
			'tools': {
				'imgur': "Imgur",
				'newrelic': "NewRelic",
				'paypal': "PayPal",
				'quicken': "Quicken",
				'recaptcha': "reCaptcha",
				'reddit': "reddit",
				'stripe': "Stripe",
				'wikidata': "WikiData",
			}
		},
	];
	
	return (<>
		{Object.entries(toolsets).map(([key, obj]) => {
			const tools = obj.tools;
			
			return (
				<div
					key={key}
					className="p-3 mb-4 border rounded bg-light"
				>
					<h4 className="h4 mb-3">
						{obj.title}
					</h4>
					
					<CardColumns>
						{Object.entries(tools).sort().map(([key, title]) => {
							let width = 120;
							let height = 60;
							
							if("javascript" === key) {
								width = 240;
							}
							
							return (
								<Card
									key={key}
								>
									<Card.Img as="div">
										<Image
										src={`/images/services/tools/${key}.svg`}
										width={width}
										height={height}
										alt={`Logo for ${title}`}
										className="img-fluid"
										style={{
											width: "100%",
											height: "auto"
										}}
									/>
									</Card.Img>
									<Card.Body className=" bg-secondary text-light">
										<Card.Title
											className="mb-0"
										>
											{title}
										</Card.Title>
									</Card.Body>
								</Card>
							);
							
							/*
								<Col
									key={key}
									sm="2"
									className="text-center bg-light mb-2"
								>
									<Image
										src={`/images/services/tools/${key}.svg`}
										width={width}
										height={height}
										alt={`Logo for ${title}`}
										className="img-fluid"
										style={{
											width: "100%",
											height: "auto"
										}}
									/>
								</Col>
								*/
						})}
					</CardColumns>
				</div>
			);
		})}
	</>);
}