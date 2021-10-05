// preset: set the jest integrate with angular.
// testMatch: Set the test folder and suffix.
// setupFilesAfterEnv: jest and angular test environment file.
// Coverage: Generate test report and the test folder target
// Mapper: Mapping path
module.exports = {
  preset: 'jest-preset-angular',
  testMatch: ['<rootDir>/src/test/spec/**/@(*.)@(spec.ts)'],
  setupFilesAfterEnv: [
    '<rootDir>/src/test.ts',
  ],
  collectCoverage: true,
  coverageReporters: ['html'],
  coverageDirectory: '<rootDir>/target/test-results/',
  moduleNameMapper: {
    'app/(.*)': '<rootDir>/src/app/$1',
    'ace-builds': '<rootDir>/node_modules/ace-builds'
  }
};