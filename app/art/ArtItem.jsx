"use client";

import Image from "next/image";
import Link from "next/link";

import styles from "./styles.module.css";

export default function ArtItem({ item }) {
	const { kind, url, title, thumb } = item;
	
	return (<>
		<Link
			href={url}
			title={`${title} in ${kind}`}
			className={styles.art_item}
		>
			<Image
				src={thumb.url}
				width={thumb.width}
				height={thumb.height}
				alt={`Preview image for ${title}`}
				className="img-fluid"
			/>
			
			<div className={styles.art_item_title}>{title}</div>
		</Link>
	</>);
}