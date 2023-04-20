// next.config.js
module.exports = {
	reactStrictMode: true,
	trailingSlash: true,
	experimental: {
		appDir: true,
	},
	output: 'standalone',   // docker specific
};
