import Link from 'next/link';

import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import Container from 'react-bootstrap/Container';
import Button from 'react-bootstrap/Button';

export default function Header() {
	return (
		<>
			<Navbar
				variant="dark"
				bg="dark"
				expand="lg"
				collapseOnSelect
			>
				<Container>
					<Navbar.Brand
						href="/"
						className="font-weight-bold"
					>
						pyxol
					</Navbar.Brand>
					<Button
						variant="secondary"
						href="/contact/"
						className="ml-auto d-sm-none"
					>
						Contact Us
					</Button>
					<Navbar.Toggle
						aria-controls="responsive-navbar-nav"
						aria-label="Toggle navigation"
						className="ml-2"
					/>
					
					<Navbar.Collapse
						id="responsive-navbar-nav"
					>
						<Nav>
							<Nav.Link
								href="/services/"
							>
								Services
							</Nav.Link>
							<Nav.Link
								href="/projects/">
								Projects
							</Nav.Link>
							<Nav.Link
								href="/art/"
							>
								Art
							</Nav.Link>
						</Nav>
					</Navbar.Collapse>
					
					<Button
						variant="secondary"
						href="/contact/"
						className="ml-auto d-none d-sm-inline-block"
					>
						Contact Us
					</Button>
				</Container>
			</Navbar>
		</>
	);
}