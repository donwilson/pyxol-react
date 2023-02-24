"use client";

import Head from 'next/head';
import Link from 'next/link';

import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import Button from 'react-bootstrap/Button';

import TwitterLogoIcon from './icons/twitter-logo';
import GithubIcon from './icons/github-logo';

export default function Header({ title }) {
	return (<>
		<Head>
			<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
			<meta name="description" content="pyxol aims to create meaningful technology through web and software development." />
			<link rel="icon" href="/favicon.ico" />
			<meta name="theme-color" content="#343a40" />
			<meta name="google-site-verification" content="AgfnDI9BToJX-7WJXnOqP8S00oYoHRBkIBBaQ8LOqD4" />
		</Head>
		
		<div className="bg-dark">
			<Navbar
				collapseOnSelect
				expand="lg"
				bg="dark"
				variant="dark"
				sticky="top"
			>
				<Navbar.Brand
					href="/"
					className="font-weight-bold"
				>
					pyxol
				</Navbar.Brand>
				
				<Button
					variant="secondary"
					href="/contact/"
					className="ml-auto d-sm-none mr-2"
				>
					Contact Us
				</Button>
				
				<Navbar.Toggle
					aria-controls="responsive-navbar-nav"
					aria-label="Toggle navigation"
				/>
				
				<Navbar.Collapse
					id="responsive-navbar-nav"
				>
					<Nav
						defaultActiveKey="/"
						className="mr-auto"
					>
						<Nav.Item>
							<Nav.Link href="/services/" className="d-block">
								Services
							</Nav.Link>
						</Nav.Item>
						<Nav.Item>
							<Nav.Link href="/projects/" className="d-block">
								Projects
							</Nav.Link>
						</Nav.Item>
						<Nav.Item>
							<Nav.Link href="/art/" className="d-block">
								Art
							</Nav.Link>
						</Nav.Item>
					</Nav>
				</Navbar.Collapse>
				
				<Link
					href="https://www.twitter.com/donwilson"
					target="_blank"
					className="ml-auto d-none d-sm-inline-block"
				>
					<TwitterLogoIcon />
					<span className="sr-only">Contact Us on Twitter</span>
				</Link>
				
				<Link
					href="https://github.com/donwilson"
					target="_blank"
					className="ml-2 d-none d-sm-inline-block ml-3"
				>
					<GithubIcon />
					<span className="sr-only">View Our GitHub</span>
				</Link>
			</Navbar>
		</div>
	</>);
}