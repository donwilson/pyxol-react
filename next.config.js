// next.config.js
module.exports = {
	reactStrictMode: true,
	trailingSlash: true,
	
	// disable X-Powered-By header
	poweredByHeader: false,
	
	// required for next.JS v13
	experimental: {
		appDir: true,
	},
	
	// required for Docker
	output: 'standalone',
};
