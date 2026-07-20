-- Flyway Migration V1: Initial Database Schema Setup
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Users Table
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    version BIGINT NOT NULL DEFAULT 0,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(30),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_by VARCHAR(100),
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    updated_date TIMESTAMP WITH TIME ZONE,
    deleted_by VARCHAR(100),
    deleted_date TIMESTAMP WITH TIME ZONE,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

-- Roles Table
CREATE TABLE roles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    version BIGINT NOT NULL DEFAULT 0,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_by VARCHAR(100),
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    updated_date TIMESTAMP WITH TIME ZONE,
    deleted_by VARCHAR(100),
    deleted_date TIMESTAMP WITH TIME ZONE,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

-- Permission Groups Table
CREATE TABLE permission_groups (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    version BIGINT NOT NULL DEFAULT 0,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_by VARCHAR(100),
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    updated_date TIMESTAMP WITH TIME ZONE,
    deleted_by VARCHAR(100),
    deleted_date TIMESTAMP WITH TIME ZONE,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

-- Permissions Table
CREATE TABLE permissions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    version BIGINT NOT NULL DEFAULT 0,
    permission_group_id UUID NOT NULL REFERENCES permission_groups(id),
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_by VARCHAR(100),
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    updated_date TIMESTAMP WITH TIME ZONE,
    deleted_by VARCHAR(100),
    deleted_date TIMESTAMP WITH TIME ZONE,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

-- Role Permissions Join Table
CREATE TABLE role_permissions (
    role_id UUID NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    permission_id UUID NOT NULL REFERENCES permissions(id) ON DELETE CASCADE,
    PRIMARY KEY (role_id, permission_id)
);

-- User Roles Join Table
CREATE TABLE user_roles (
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_id UUID NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

-- Organizations Table
CREATE TABLE organizations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    version BIGINT NOT NULL DEFAULT 0,
    name VARCHAR(150) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    tax_id VARCHAR(50),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_by VARCHAR(100),
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    updated_date TIMESTAMP WITH TIME ZONE,
    deleted_by VARCHAR(100),
    deleted_date TIMESTAMP WITH TIME ZONE,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

-- System Settings Table
CREATE TABLE system_settings (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    version BIGINT NOT NULL DEFAULT 0,
    setting_key VARCHAR(100) NOT NULL UNIQUE,
    setting_value TEXT NOT NULL,
    description VARCHAR(255),
    created_by VARCHAR(100),
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100),
    updated_date TIMESTAMP WITH TIME ZONE,
    deleted_by VARCHAR(100),
    deleted_date TIMESTAMP WITH TIME ZONE,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

-- Audit Logs Table
CREATE TABLE audit_logs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    trace_id VARCHAR(100),
    username VARCHAR(150),
    action VARCHAR(100) NOT NULL,
    entity_name VARCHAR(100),
    entity_id VARCHAR(100),
    details TEXT,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Performance Indexes
CREATE INDEX idx_users_email_deleted ON users(email, is_deleted);
CREATE INDEX idx_audit_logs_created_date ON audit_logs(created_date DESC);
CREATE INDEX idx_audit_logs_trace_id ON audit_logs(trace_id);
