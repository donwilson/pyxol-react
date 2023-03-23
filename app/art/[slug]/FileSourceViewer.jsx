import SyntaxHighlighter from "react-syntax-highlighter/dist/esm/default-highlight";
import { dark } from "react-syntax-highlighter/dist/esm/styles/hljs";

export default function FileSourceViewer({
	file,
	type,
	contents
}) {
	return (<>
		<div key={file}>
			<h3 className="h3 mb-2">
				{file}
			</h3>
			
			<SyntaxHighlighter
				language={type}
				style={dark}
			>
				{contents}
			</SyntaxHighlighter>
		</div>
	</>);
}