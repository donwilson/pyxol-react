import Head from 'next/head';

import Header from '../components/header';

export default function Home() {
	return (
		<>
			<Head>
				<title>Build Great Things | pyxol</title>
				<meta name="description" content="pyxol aims to create meaningful technology through web and software development." />
				<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
				<link rel="icon" href="/favicon.ico" />
				<meta name="theme-color" content="#343a40" />
				<meta name="google-site-verification" content="AgfnDI9BToJX-7WJXnOqP8S00oYoHRBkIBBaQ8LOqD4" />
			</Head>
			
			<Header />
		</>
	)
}
