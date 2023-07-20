import { Light as SyntaxHighlighter } from 'react-syntax-highlighter';
import dark from 'react-syntax-highlighter/dist/esm/styles/hljs/dark';
import js from 'react-syntax-highlighter/dist/esm/languages/hljs/javascript';
import css from 'react-syntax-highlighter/dist/esm/languages/hljs/css';
import java from 'react-syntax-highlighter/dist/esm/languages/hljs/java';

export default function FileSourceViewer({
	file,
	type,
	contents
}) {
	SyntaxHighlighter.registerLanguage('javascript', js);
	SyntaxHighlighter.registerLanguage('css', css);
	SyntaxHighlighter.registerLanguage('java', java);
	
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