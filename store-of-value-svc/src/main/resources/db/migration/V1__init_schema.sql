-- Create the accounts table
CREATE TABLE IF NOT EXISTS accounts
(
    id          UUID PRIMARY KEY,
    customer_id UUID           NOT NULL,
    balance     DECIMAL(19, 4) NOT NULL,
    active      BOOLEAN        NOT NULL,
    version     BIGINT
);

-- Create an index on customer_id for better query performance
CREATE INDEX IF NOT EXISTS idx_accounts_customer_id ON accounts (customer_id);