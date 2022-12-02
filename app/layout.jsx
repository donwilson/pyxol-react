import "../styles/custom.scss";

// Layouts must accept a children prop
// This will be populated with nested layouts or pages
export default function RootLayout({ children }) {
	return (
		<html lang="en">
			<body>{children}</body>
		</html>
	);
}