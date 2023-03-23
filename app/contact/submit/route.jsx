const formData = require('form-data');
const Mailgun = require('mailgun.js');

//import { NextResponse } from "next/server";

//export const dynamicParams = true;

/* export function GET(request) {
	console.log(request);
	
	return NextResponse.json({
		'status': "SUCCESS",
		'method': "GET"
	});
} */

function generateEmailBody(name, email, message) {
	let date_ins = new Date();
	
	let bits = [];
	
	bits.push("A new message was sent from the pyxol.com contact form.\n\n");
	bits.push("Date: "+ date_ins.toUTCString() +"\n\n");
	bits.push("============\n\n");
	bits.push("Sender Name: "+ name +"\n\n");
	bits.push("Sender Email: "+ email +"\n\n");
	bits.push("Message:\n\n"+ message +"\n\n");
	
	return bits.join("");
}

export async function POST(request) {
	try {
		const rawURLParams = await request.text();
		//console.log("rawURLParams=", rawURLParams);
		
		let params = new URLSearchParams("?"+ rawURLParams);
		
		let name = params.get('name') || "";
		name = name.trim();
		
		if("" === name) {
			throw "Please include a name with your inquiry";
		}
		
		let email = params.get('email') || "";
		email = email.trim();
		
		if("" === email) {
			throw "Please include an email address with your inquiry";
		}
		
		// validate message body
		let message = params.get('message') || "";
		message = message.trim();
		
		if("" === message) {
			throw "Please include a message with your inquiry";
		}
		
		let email_body = generateEmailBody(name, email, message);
		
		let email_date_ins = new Date();
		
		// ready to send
		const mailgun = new Mailgun(formData);
		const mg = mailgun.client({
			username: 'api',
			key: process.env.MAILGUN_API_KEY
		});
		
		mg.messages.create(process.env.MAILGUN_DOMAIN, {
			'from': process.env.CONTACT_FORM_FROM,
			'to': process.env.CONTACT_FORM_TO,
			'subject': "Contact Message - "+ email_date_ins.getFullYear().toString() +"-"+ email_date_ins.getMonth().toString().padStart(2, "0") +"-"+ email_date_ins.getDate().toString().padStart(2, "0") +"",
			'text': email_body
		});
		
		
		// successful response
		return Response.json({
			'status': "success",
			'cargo': {
				'name': name,
				'email': email,
				'message': message,
			},
			'message': "Successfully sent your message",
		});
	} catch(err) {
		return Response.json({
			'status': "error",
			'message': err.message,
		});
	}
	
}