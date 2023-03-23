import { Suspense } from "react";

export const metadata = {
	'title': `Web Development Services | ${process.env.NEXT_PUBLIC_SITE_NAME}`
};

export default function Layout({ children }) {
	return (<>
		<Suspense fallback={<p>Loading page...</p>}>
			{children}
		</Suspense>
	</>);
}