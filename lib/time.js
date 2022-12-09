const _ = require('lodash');

export function ts() { return Math.floor( (_.now() / 1000) ); }