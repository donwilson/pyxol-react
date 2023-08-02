"use client";

import { useState } from "react";

import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Alert from "react-bootstrap/Alert";

export default function ContactForm() {
	// warning message about form
	const [formWarning, setFormWarning] = useState("");
	
	// show/hide form based on submission status
	const [showForm, setShowForm] = useState(true);
	
	// status of form being submitted
	const [formLoading, setFormLoading] = useState(false);
	
	// message from form submission response
	const [formResponse, setFormResponse] = useState("");
	
	// success/danger status from form submission response
	const [formSubmitStatus, setFormSubmitStatus] = useState("");
	
	const handleFormSubmit = async (event) => {
		try {
			event.preventDefault();
			
			// set form as loading
			setFormLoading(true);
			
			const form_data = new FormData(event.target);
			const submit_data = new URLSearchParams(form_data);
			
			await fetch(event.target.action, {
				'method': event.target.method,
				'body': submit_data,
				'headers': {
					'accept': "application/json",
				},
			}).then((res) => res.json()).then((data) => {
				if(!data || !data.status || ("error" === data.status)) {
					// error
					let msg = "Failed to send message, please try again";
					
					if(data.message) {
						msg = data.message;
					}
					
					setFormWarning(msg);
					setShowForm(true);
					setFormLoading(false);
					
					return;
				}
				
				// success
				let msg = "Successfully sent message";
				
				if(data.message) {
					msg = data.message;
				}
				
				setShowForm(false);
				setFormResponse(msg);
				setFormSubmitStatus("success");
			});
		} catch(err) {
			setFormLoading(false);
			setFormWarning("Error: "+ err);
		}
	};
	
	if(!showForm) {
		return (<>
			<Alert variant={formSubmitStatus}>
				{formResponse}
			</Alert>
		</>);
	}
	
	return (<>
		{formWarning?(<>
			<Alert variant="warning">
				{formWarning}
			</Alert>
		</>):""}
		
		<Form action="/contact/submit/" method="POST" onSubmit={handleFormSubmit}>
			<Form.Group controlId="contactForm_name">
				<Form.Label>Name</Form.Label>
				<Form.Control
					type="text"
					name="name"
					placeholder="Your Name"
					disabled={formLoading}
				/>
			</Form.Group>
			<Form.Group controlId="contactForm_email">
				<Form.Label>Email</Form.Label>
				<Form.Control
					type="email"
					name="email"
					placeholder="email@address.com"
					disabled={formLoading}
				/>
				<Form.Text className="text-muted">
					We&apos;ll contact you here
				</Form.Text>
			</Form.Group>
			<Form.Group controlId="contactForm_message">
				<Form.Label>Message</Form.Label>
				<Form.Control
					as="textarea"
					name="message"
					rows={5}
					placeholder="What would you like to discuss with us?"
					disabled={formLoading}
				/>
			</Form.Group>
			
			<Button
				variant="primary"
				type="submit"
				disabled={formLoading}
			>
				Send Message
			</Button>
		</Form>
	</>);
}