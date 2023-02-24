import Image from "next/image";

import { Row, Col } from "react-bootstrap";

export default function ProgrammingLanguagesList() {
	return (<>
		<Row>
			<Col
				sm="2"
				className="text-center"
			>
				<Image
					src="/images/services/logo-php.svg"
					width="100"
					height="50"
					alt="PHP Logo"
					className="img-responsive"
					style={{
						width: "100%",
						height: "auto"
					}}
				/>
			</Col>
			<Col
				sm="2"
				className="text-center"
			>
				<Image
					src="/images/services/logo-php.svg"
					width="100"
					height="50"
					alt="PHP Logo"
					className="img-responsive"
					style={{
						width: "100%",
						height: "auto"
					}}
				/>
			</Col>
			<Col
				sm="2"
				className="text-center"
			>
				<Image
					src="/images/services/logo-php.svg"
					width="100"
					height="50"
					alt="PHP Logo"
					className="img-responsive"
					style={{
						width: "100%",
						height: "auto"
					}}
				/>
			</Col>
		</Row>
	</>);
}