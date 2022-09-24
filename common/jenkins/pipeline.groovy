[
  [
    name    : 'Build',
    command : 'mvn --no-transfer-progress clean install',
    shared  : false,
  ],
  [
    name    : 'Deploy',
    command : 'mvn --no-transfer-progress clean install -P build-docker-image',
    shared  : false,
  ]
]
