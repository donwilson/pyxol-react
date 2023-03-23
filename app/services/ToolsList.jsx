import Image from "next/image";

import { Row, Col } from "react-bootstrap";

export default function ProgrammingLanguagesList() {
	const languages = {
		'apache_solr': "Apache SOLR",
		'apache': "Apache",
		'aws_lambda': "AWS Lambda",
		'aws': "AWS",
		'bitbucket': "BitBucket",
		'bitnami': "Bitnami",
		'centos': "CentOS",
		'cloudflare': "CloudFlare",
		'css': "CSS",
		'curl_haxx': "cURL",
		'digitalocean': "DigitalOcean",
		'docker': "Docker",
		'edgewall_trac': "trac",
		'firefox': "Firefox",
		'fontawesome': "Font Awesome",
		'github': "GitHub",
		'gitlab': "GitLab",
		'git-scm': "git-scm",
		'godaddy': "GoDaddy",
		'google_chrome': "Google Chrome",
		'google_cloud': "Google Cloud",
		'grafana': "Grafana",
		'graphql': "GraphQL",
		'handlebarsjs': "handlebars.js",
		'haproxy': "HAProxy",
		'heroku': "Heroku",
		'html5': "HTML5",
		'imgur': "Imgur",
		'javascript': "JavaScript",
		'java': "Java",
		'jquery': "jQuery",
		'js_webpack': "WebPack",
		'kubernetes': "Kubernetes",
		'laravel': "Laravel",
		'letsencrypt': "Let's Encrypt",
		'linode': "Linode",
		'mailgun': "Mailgun",
		'mariadb': "MariaDB",
		'memcached': "memcached",
		'microsoft_edge': "Microsoft Edge",
		'minioio': "minio",
		'mongodb': "mongoDB",
		'mysql': "MySQL",
		'networksolutions': "NetworkSolutions",
		'newrelic': "NewRelic",
		'nginx': "nginx",
		'nodejs': "Node.js",
		'npmjs': "npm",
		'opengraph': "OpenGraph",
		'paypal': "PayPal",
		'perl': "Perl",
		'phaserio': "phaser.io",
		'php': "PHP",
		'quicken': "Quicken",
		'rabbitmq': "RabbitMQ",
		'rackspace': "RackSpace",
		'rancher': "Rancher",
		'raspberrypi': "Raspberry PI",
		'react': "React",
		'recaptcha': "reCaptcha",
		'redhat': "RedHat",
		'redis': "Redis",
		'sass': "SASS",
		'sketchapp': "SketchApp",
		'slack': "Slack",
		'sqlite': "SQLite",
		'stripe': "Stripe",
		'tailwindcss': "Tailwind CSS",
		'traefikio': "Traefik",
		'trello': "Trello",
		'typescript': "TypeScript",
		'vagrantup': "Vagrant UP",
		'varnish-cache': "Varnish Cache",
		'virtualbox': "VirtualBox",
		'visualstudio_code': "Visual Studio Code",
		'vuejs': "VueJS",
		'wikidata': "WikiData",
		//'wordpress': "WordPress",
		'xml': "XML",
	};
	
	return (<>
		<Row>
			{Object.entries(languages).map(([key, title]) => {
				let width = 120;
				let height = 60;
				
				if("javascript" === key) {
					width = 240;
				}
				
				return (
					<Col
						key={key}
						sm="2"
						className="text-center"
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
				);
			})}
		</Row>
	</>);
}