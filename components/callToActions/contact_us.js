"use client";

import Button from "react-bootstrap/Button";

export default function ContactUsCTA() {
	return (
		<>
			<div
				className="bg-secondary text-white text-center mt-4 p-4"
			>
				please visit our
				<Button
					variant="light"
					href="/contact/"
					title="Get in touch with pyxol"
					className="text-secondary mx-2 font-weight-bold"
				>
					Contact Us
				</Button>
				page to get in touch
			</div>
		</>
	)
}