import Footer from "../components/footer";
import Header from "../components/header";
import "../styles/custom.scss";

export const metadata = {
	'title': `Build Great Things | ${process.env.NEXT_PUBLIC_SITE_NAME}`,
	'description': "pyxol aims to create meaningful technology through web and software development.",
	'viewport': "width=device-width, initial-scale=1, shrink-to-fit=no",
	'theme-color': "#343a40",
	'google-site-verification': "AgfnDI9BToJX-7WJXnOqP8S00oYoHRBkIBBaQ8LOqD4"
};

// Layouts must accept a children prop
// This will be populated with nested layouts or pages
export default function RootLayout({ children }) {
	return (
		<html lang="en">
			<body>
				<Header />
				
				{children}
				
				<Footer />
			</body>
		</html>
	);
}